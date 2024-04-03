// Utilities
import { defineStore } from "pinia";
import {
  requestGetStory,
  requestDeleteStory,
  requestGetStories,
} from "@/api/story";
export const useStoryStore = defineStore("story", () => {
  const getStory = (id: number, success: any, error: any) => {
    requestGetStory(
      id,
      (res) => {
        success(res);
      },
      (err) => {
        error(err);
      }
    );
  };
  const getStories = (success: any, error: any) => {
    requestGetStories(
      (res) => {
        success(res);
      },
      (err) => {
        error(err);
      }
    );
  };
  const deleteStory = (id: number, success: any, error: any) => {
    requestDeleteStory(id, success, error);
  };

  return {
    getStory,
    getStories,
    deleteStory,
  };
});
