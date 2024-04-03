import { axiosInstance } from "@/utils/http-commons";
import { SongLikeRequest } from "@/types";
const instance = axiosInstance();

const API_URL = "/likes";

function requestPostStoryLike(success: any, error: any) {
  instance.post(`${API_URL}/story`).then(success).catch(error);
}

function requestGetStoryLike(
  id: number,
  userId: number,
  success: any,
  error: any
) {
  instance.get(`${API_URL}/story/${id}/${userId}`).then(success).catch(error);
}

function requestDeleteStoryLike(
  id: number,
  userId: number,
  success: any,
  error: any
) {
  instance
    .delete(`${API_URL}/story/${id}/${userId}`)
    .then(success)
    .catch(error);
}

function requestPostSongLike(req: SongLikeRequest, success: any, error: any) {
  instance.post(`${API_URL}/song`, req).then(success).catch(error);
}

function requestGetSongLike(
  id: number,
  userId: number,
  success: any,
  error: any
) {
  instance.get(`${API_URL}/song/${id}/${userId}`).then(success).catch(error);
}

function requestDeleteSongLike(
  id: number,
  userId: number,
  success: any,
  error: any
) {
  instance.delete(`${API_URL}/song/${id}/${userId}`).then(success).catch(error);
}

export {
  requestPostStoryLike,
  requestDeleteStoryLike,
  requestGetStoryLike,
  requestDeleteSongLike,
  requestGetSongLike,
  requestPostSongLike,
};
