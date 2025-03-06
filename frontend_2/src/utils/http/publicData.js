async function getPopularChallenges() {
    const response = await fetch('https://localhost:8080/api/home-challenges');
    console.log(response);

    return await response.json();
}

export {getPopularChallenges};