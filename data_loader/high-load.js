require('isomorphic-fetch');


const call = async () => {

    const result = await fetch("https://springbootapp.ecs.awsdevbot.com/api/movies/top", {
        method: "get",
        ssl: false,
        headers: {
            'content-type': 'application/json',
            Authorization: process.env.GOOGLE_JWT_ID_TOKEN
        }
    });
    if (result.status >= 400) {
        throw new Error("Bad response from server");
    }
    return await result.json();

};

const delay = ms => new Promise(resolve => setTimeout(resolve, ms))

const callTopTenRatedMovies =  callsNumber => Promise.all(Array.from(Array(callsNumber).keys()).map(() => call()));

const run = async () => {
    for (const it of Array.from(Array(10000).keys())) {
        await delay(50);
        await callTopTenRatedMovies(116);
        console.log(it);

    }
}

run();