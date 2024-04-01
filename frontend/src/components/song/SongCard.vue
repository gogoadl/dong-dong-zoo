<script setup lang="ts">
import { ref, defineProps } from "vue";
import { RouterLink } from "vue-router";
import { RefSong } from "@/types";

const props = defineProps<{
  song: RefSong;
}>();
</script>

<template>
  <RouterLink
    v-if="props.song"
    class="router-link"
    :to="{ name: 'songDetail', params: { songId: props.song.songId } }"
  >
    <v-card class="songcard">
      <!--      서버용-->
      <!-- <v-img
        class="white--text"
        height="200px"
        :src="props.song.songImageUrl + '/0.png'"
        style="margin: 10px"
      > -->
      <!--        로컬용 이미지-->
      <v-img
        class="white&#45;&#45;text"
        height="200px"
        :src="`/src` + props.song.songImageUrl + '/0.png'"
        style="margin: 10px"
      >
        <v-container fill-height fluid>
          <v-layout fill-height>
            <v-flex xs12 align-end flexbox>
              <span class="headline"></span>
            </v-flex>
          </v-layout>
        </v-container>
      </v-img>
      <v-col cols="auto" align="center">
        <h3>{{ props.song.songName }}</h3>
      </v-col>
      <v-row cols="12">
        <v-col cols="6" class="card-body">
          <font-awesome-icon :icon="['fas', 'eye']" style="opacity: 50%" />
          {{ props.song.views }}
        </v-col>
        <v-col cols="6" class="card-body">
          <font-awesome-icon :icon="['fas', 'heart']" style="opacity: 50%" />
          {{ props.song.likeCount }}
        </v-col>
      </v-row>
      <br />
    </v-card>
  </RouterLink>
</template>

<style scoped>
.songcard {
  height: 300px;
  border-radius: 24px;
}

.card-body {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
