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

  return instance;
}

export { axiosInstance };
