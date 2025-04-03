import { requestData } from "./requests";

export async  function logIn(email, password) {
  const response = await requestData({
    url: "api/auth/authenticate",
    method: "POST",
    body: {
      email,
      password,
    },
    auth: false,
  });
  console.log(response);
  if (response.status === 30){
    localStorage.setItem("token", response.token);
  }
  return response;

}