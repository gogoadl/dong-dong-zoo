import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { Authentication, SignUp, SongLikeRequest, User } from "@/types";
import { requestLogin, requestSignUp } from "@/api/auth";
import { requestgetLikeSongs } from "@/api/profiles";
import {
  requestGetSongLike,
  requestGetStoryLike,
  requestPostSongLike,
  requestPostStoryLike,
  requestDeleteSongLike,
  requestDeleteStoryLike,
} from "@/api/like";
import { HttpStatusCode } from "axios";
export const useUserStore = defineStore(
  "user",
  () => {
    const user = ref<User>(null);
    const getUser = () => {
      return user.value;
    };
    const fetchDummyUser = async () => {
      const dummyUser = {
        user: 1,
      };
      user.value = dummyUser;
    };

    const postLogin = async (data: string, success: any, error: any) => {
      requestLogin(data, success, error);
    };
    const postSignUp = async (data: SignUp, success: any, error: any) => {
      requestSignUp(data, success, error);
    };
    const getSongLike = (id: number, success: any, error: any) => {
      requestGetSongLike(id, Number(user.value), success, error);
    };
    const getStoryLike = (id: number, success: any, error: any) => {
      requestGetSongLike(id, Number(user.value), success, error);
    };
    const postSongLike = (songId: number, success: any, error: any) => {
      const request = {
        userId: Number(user.value),
        songId: songId,
      } as SongLikeRequest;
      requestPostSongLike(request, success, error);
    };
    const postStoryLike = (id: number, success: any, error: any) => {
      // requestPostStoryLike(id, Number(user.value), success, error);
    };
    const deleteSongLike = (id: number, success: any, error: any) => {
      requestDeleteSongLike(id, Number(user.value), success, error);
    };
    const deleteStoryLike = (id: number, success: any, error: any) => {
      requestDeleteStoryLike(id, Number(user.value), success, error);
    };
    const getLikeSongs = (success: any, error: any) => {
      requestgetLikeSongs(Number(user.value), success, error);
    };
    const logout = () => {
      user.value = null;
    };
    return {
      fetchDummyUser,
      postLogin,
      postSignUp,
      getStoryLike,
      getSongLike,
      postStoryLike,
      postSongLike,
      deleteStoryLike,
      deleteSongLike,
      getLikeSongs,
      logout,
      user,
      getUser,
    };
  },
  {
    persist: true,
  }
);
