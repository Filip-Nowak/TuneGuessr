import { requestData } from "./requests";

export async function getHeaderUserData() {
  const token = localStorage.getItem("token");
  if (token === null || token === undefined || token === "") {
    return null;
  }
  try{
  const response = await requestData({
    url: "api/user",
    method: "GET",
    auth: true,
  });
  console.log(response);
  return response;
  }catch (error) {
    console.error("Error fetching user data:", error);
    return null;
  }
}
