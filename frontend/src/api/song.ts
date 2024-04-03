import { axiosInstance } from "@/utils/http-commons";

const instance = axiosInstance();

const API_URL = "/songs";

function requestGetSongs(success: any, error: any) {
  instance.get(`${API_URL}`).then(success).catch(error);
}

function requestGetSong(id: string, success: any, error: any) {
  instance.get(`${API_URL}/${id}`).then(success).catch(error);
}

function requestCreateSong(id: string, tag: string, success: any, error: any) {
  instance.get(`${API_URL}/create/${id}?tag=${tag}`).then(success).catch(error);
}

export { requestGetSong, requestGetSongs, requestCreateSong };
