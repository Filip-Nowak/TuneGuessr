async function getPopularChallenges() {
    const response = await fetch('https://localhost:8080/api/home-challenges');
    console.log(response);

    return await response.json();
}
async function getChallengeById(id){
    const response = await fetch('https://localhost:8080/api/challenge/'+id);
    const xd =await response.json();
    return xd.data;
}
export {getPopularChallenges, getChallengeById};