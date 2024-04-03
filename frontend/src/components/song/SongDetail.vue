<script setup lang="ts">
import LocalStoryBook from "./LocalStoryBook.vue";
import StoryKeyword from "./StoryKeyword.vue";
import { ref, onMounted, watch } from "vue";
import { useSongStore } from "@/store/song";
import { useRoute, useRouter } from "vue-router";
import { HttpStatusCode } from "axios";
import { RefSongDetail } from "@/types";
import { useUserStore } from "@/store/user";
const assetPath = import.meta.env.VITE_ASSET_PATH;
const songStore = useSongStore();
const userStore = useUserStore();
const route = useRoute();
const router = useRouter();
const songId = route.params.songId;

console.log(songId);
const fullImageUrl = ref<string>("");
const songDetail = ref<RefSongDetail>(null);
const pageNumber = ref(1);

const isBlue = ref(false);

const toggleHeart = () => {
  if (isBlue.value) {
    userStore.deleteSongLike(
      songDetail.value.songId,
      (res) => {
        console.log(res.data);
        songDetail.value.likeCount--;
      },
      (err) => {
        console.log(err);
      }
    );
  } else {
    userStore.postSongLike(
      songDetail.value.songId,
      (res) => {
        console.log(res.data);
        songDetail.value.likeCount++;
      },
      (err) => {
        console.log(err);
      }
    );
  }
  isBlue.value = !isBlue.value;
};

const goBack = () => {
  console.log("call goback");
  router.go(-1);
};

watch(songDetail, () => {
  fullImageUrl.value = `${songDetail.value.songImageUrl}` + "0.png";
  pageNumber.value = songDetail.value.lyricsList.length;
  console.log(fullImageUrl.value);
  console.log(pageNumber.value);
});

// 컴포넌트가 마운트된 후 실행되는 동작 정의
onMounted(() => {
  // songId를 사용하여 songDetail을 가져오고 fullImageUrl 값을 설정
  songStore.getSong(
    songId as string,
    (res) => {
      if (res.status == HttpStatusCode.Ok) {
        console.log(res.data);
        songDetail.value = res.data;
        userStore.getSongLike(
          songDetail.value.songId,
          (res) => {
            if ((res.status = HttpStatusCode.Ok)) {
              console.log(res.data);
              if (res.data == true) {
                isBlue.value = true;
              } else {
                isBlue.value = false;
              }
            }
          },
          (err) => {}
        );
      }
    },
    (err) => {
      console.error(err);
    }
  );
});
</script>

<template>
  <v-row>
    <v-container>
      <v-row rows="12" class="ma-2 mt-2 pa-0">
        <v-col rows="3">
          <v-btn @click="goBack" style="background: none" elevation="0"
            ><h3>< 뒤로가기</h3></v-btn
          >
        </v-col>
        <v-col></v-col>
        <v-col></v-col>
        <v-col></v-col>
        <v-col></v-col>
        <v-col rows="3" class="info-container">
          <v-row rows="12" class="ms-3">
            <v-col cols="auto" class="ma-0 mt-1">
              <div
                class="rounded-circle ma-0"
                style="cursor: pointer"
                @click="toggleHeart"
              >
                <font-awesome-icon
                  v-if="isBlue"
                  :icon="['fas', 'heart']"
                  style="color: skyblue"
                />
                <font-awesome-icon
                  v-else
                  :icon="['fas', 'heart']"
                  style="opacity: 50%; color: black"
                />
              </div>
            </v-col>
            <v-col cols="auto" class="ma-0">{{ songDetail?.likeCount }}</v-col>
            <v-col cols="auto" class="ma-0 mt-1">
              <font-awesome-icon :icon="['fas', 'eye']" style="opacity: 50%" />
            </v-col>
            <v-col cols="auto" class="ma-0">{{ songDetail?.views }}</v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-row rows="12" class="detail-container" style="height: 400px">
        <v-row rows="5" class="pa-4 ma-0">
          <v-img class="imgcontent" :src="fullImageUrl"></v-img>
        </v-row>
        <v-row rows="7">
          <v-col cols="2" align="end">
            <v-row class="title_content">
              <v-col cols="12" class="ma-2"></v-col>
              <v-col cols="12" class="ma-1"><h2>동요 제목</h2></v-col>
              <v-col cols="12" class="ma-1"><h2>동요 가사</h2></v-col>
            </v-row>
          </v-col>
          <v-col cols="8">
            <v-row>
              <v-col cols="12" class="ma-2"></v-col>
              <v-col cols="12" class="info-container ma-2">
                <h3>{{ songDetail?.songName }}</h3></v-col
              >
              <v-col
                cols="auto"
                class="ma-2 info-container"
                style="width: 100%"
              >
                <div v-for="lyrics in songDetail?.lyricsList">
                  {{ lyrics }}
                </div>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-row>
    </v-container>
  </v-row>
  <v-row>
    <v-container>
      <StoryKeyword :songDetail="songDetail"></StoryKeyword>
    </v-container>
  </v-row>

  <v-container>
    <LocalStoryBook :songDetail="songDetail" :pageNumber="pageNumber" />
  </v-container>
</template>

<style>
.info-container {
  background-color: rgb(255, 215, 254);
  border-radius: 16px;
}

.detail-container {
  background-color: rgb(253, 253, 253);
  border-radius: 24px;
  margin-left: 24px;
}

.radius-12 {
  border-radius: 12px;
}

.imgcontent {
  border-radius: 24px;
  width: 100%;
}

.song-preview {
  background: url("@/assets/character_2.png");
  background-size: cover;
  transition: transform 0.3s ease;
}

.song-preview:hover {
  transform: scale(1.1); /* 호버 시 이미지를 1.1배 확대 */
}

.create-story {
  background: url("../../assets/character_3.png");
  background-size: cover;
  transition: transform 0.3s ease;
}

.create-story:hover {
  transform: scale(1.1); /* 호버 시 이미지를 1.1배 확대 */
}
</style>
