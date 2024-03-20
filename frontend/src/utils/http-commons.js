import axios from "axios";

const { VITE_API_USER_URL } = import.meta.env;

function axiosInstance() {
  const instance = axios.create({
    baseURL: VITE_API_NEWS_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  return instance;
}

export { axiosInstance };
