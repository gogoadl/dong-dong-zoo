import axios from "axios";

const VITE_API_URL = import.meta.env.VITE_API_URL;

function axiosInstance() {
  console.log(VITE_API_URL);
  const instance = axios.create({
    baseURL: VITE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    timeout: 0,
  });
  // instance.interceptors.request.use((request) => {
  //   console.log("Starting Request", JSON.stringify(request, null, 2));
  //   return request;
  // });

  // instance.interceptors.response.use((response) => {
  //   console.log("Response:", JSON.stringify(response, null, 2));
  //   return response;
  // });
  return instance;
}

export { axiosInstance };
