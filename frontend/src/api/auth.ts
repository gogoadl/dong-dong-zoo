import { axiosInstance } from "@/utils/http-commons";
import { Authentication, SignUp } from "@/types";
const instance = axiosInstance();

const API_URL = "/auth";

function requestLogin(data: string, success: any, error: any) {
  instance
    .post(
      `${API_URL}/login`,
      {},
      {
        headers: {
          Authorization: `Basic ${data}`,
        },
      }
    )
    .then(success)
    .catch(error);
}

function requestLogout(auth: Authentication, success: any, error: any) {
  instance.post(`${API_URL}/logout`, auth).then(success).catch(error);
}

function requestSignUp(data: SignUp, success: any, error: any) {
  instance.post(`${API_URL}/signup`, data).then(success).catch(error);
}

export { requestLogin, requestLogout, requestSignUp };
