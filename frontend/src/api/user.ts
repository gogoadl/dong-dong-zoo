import { axiosInstance } from "@/utils/http-commons";
import { Authentication } from "@/types";
const instance = axiosInstance();

const API_URL = "/user";

function requestLogin(data: string, success: any, error: any) {
  instance.post(`${API_URL}`, data).then(success).catch(error);
}

export { requestLogin };
