<script setup lang="ts">
import SongCard from "./SongCard.vue";
import { ref, onMounted } from "vue";
import { useUserStore } from "@/store/user";
import { HttpStatusCode } from "axios";
import { RefSong } from "@/types";

const userStore = useUserStore();

const songList = ref<RefSong[]>(null);
onMounted(() => {
  userStore.getLikeSongs(
    (res) => {
      if (res.status == HttpStatusCode.Ok) {
        console.log(res.data);
        songList.value = res.data;
      }
    },
    (err) => {
      console.error(err);
    }
  );
});
</script>

<template>
  <div class="songTitle">
    <h1 style="">동요 리스트</h1>
  </div>

  <v-container class="list">
    <div class="songlist">
      <p v-for="song in songList" style="margin: 24px">
        <SongCard :song="song"></SongCard>
      </p>
    </div>
  </v-container>
</template>

<style scoped>
.songTitle {
  text-align: center;
}

.list {
  justify-content: center;
}

.songlist {
  display: grid;
  justify-content: center;
  grid-template-columns: repeat(3, 1fr);
}

.song_content {
  width: 50px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
