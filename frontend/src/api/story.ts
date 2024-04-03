import { axiosInstance } from "@/utils/http-commons";

const instance = axiosInstance();

const API_URL = "/stories";

function requestGetStories(success: any, error: any) {
  instance.get(`${API_URL}`).then(success).catch(error);
}

function requestGetStory(id: number, success: any, error: any) {
  instance.get(`${API_URL}/${id}`).then(success).catch(error);
}

function requestDeleteStory(id: number, success: any, error: any) {
  instance.delete(`${API_URL}/${id}`).then(success).catch(error);
}

export { requestGetStories, requestGetStory, requestDeleteStory };
