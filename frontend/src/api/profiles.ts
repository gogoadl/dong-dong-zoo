import { axiosInstance } from "@/utils/http-commons";

const instance = axiosInstance();

const API_URL = "/profiles";

function requestgetLikeSongs(userId: number, success: any, error: any) {
  instance.get(`${API_URL}/songlike/${userId}`).then(success).catch(error);
}

export { requestgetLikeSongs };
