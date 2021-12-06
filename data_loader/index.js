require('isomorphic-fetch');

const csv = require('csv-parser');
const fs = require('fs');

const moviesSql = fs.createWriteStream('movies.sql', {
    flags: 'a'
});

const results = [];

function getYear({Year}) {
    return !Year.includes("/") ? Year.split(" ")[0] : Year.split("/")[0];
}

function didItWin(it) {
    return it["Won?"] === "YES" ? 1 : 0;
}

function getTitle(it) {
    return !(it.Year.includes("(1st)") || it.Year.includes("(2nd)")) ? it.Nominee : it["Additional Info"];
}

function getBoxOffice(data) {
    return data.BoxOffice.replace("$","").replaceAll(",","");
}

fs.createReadStream('academy_awards.csv')
    .pipe(csv())
    .on('data', (data) => results.push(data))
    .on('end', async () => {
        const movies = results.filter(it => it.Category === 'Best Picture');
        for (const it of movies) {
            const title = getTitle(it);
            const year = getYear(it);
            const response = await fetch(`http://www.omdbapi.com/?type=movie&t=${title}&y=${year}&apikey=${process.env.OMDB_API_KEY}`)
            if (response.status >= 400) {
                throw new Error("Bad response from server");
            }
            const data = await response.json();
            if (data.Response === "True") {
                const boxOffice = getBoxOffice(data);
                if (!isNaN(boxOffice)) {
                    const dataRow = "INSERT INTO \`movie\` (\`title\`, \`year\`, \`is_best_picture\`, \`box_office_value\`) VALUES (\"" + title + "\", " + year + ", " + didItWin(it) + ", " + boxOffice + ");\n"
                    console.log(dataRow)
                    moviesSql.write(dataRow);
                } else {
                    console.warn(data);
                }
            } else {
                console.warn(data);
            }
        }
        moviesSql.end();
    });

