<script setup lang="ts">
import { ref } from "vue";
import { useUserStore } from "@/store/user";
import { Authentication } from "@/types";
import { RouterLink, useRouter } from "vue-router";
import { HttpStatusCode } from "axios";

const userStore = useUserStore();
const router = useRouter();

const auth = ref<Authentication>({
  email: "",
  password: "",
});
const showPassword = ref(false);
const emailErrors = ref<string[]>([]);
const passwordErrors = ref<string[]>([]);
const loginErrors = ref<string[]>([]);

const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]/;

const clearEmailError = () => {
  emailErrors.value = [];
};

const clearPasswordError = () => {
  passwordErrors.value = [];
};

const clearLoginError = () => {
  loginErrors.value = [];
};

const validateForm = () => {
  let valid = true;
  if (!emailPattern.test(auth.value.email)) {
    emailErrors.value.push("올바른 이메일 형식이 아닙니다.");
    valid = false;
  }
  if (auth.value.password.length < 8) {
    passwordErrors.value.push("비밀번호는 최소 8자 이상이어야 합니다.");
    valid = false;
  }
  // if (!/(?=.*[a-z])(?=.*[A-Z])/.test(auth.value.password)) {
  //   passwordErrors.value.push(
  //     "비밀번호는 최소한 하나의 대문자와 소문자를 포함해야 합니다."
  //   );
  //   valid = false;
  // }
  // if (!/\d/.test(auth.value.password)) {
  //   passwordErrors.value.push(
  //     "비밀번호는 최소한 하나의 숫자를 포함해야 합니다."
  //   );
  //   valid = false;
  // }
  // if (!/[^A-Za-z0-9]/.test(auth.value.password)) {
  //   passwordErrors.value.push(
  //     "비밀번호는 최소한 하나의 특수문자를 포함해야 합니다."
  //   );
  //   valid = false;
  // }
  return valid;
};

const handleSubmit = () => {
  clearLoginError();
  if (validateForm()) {
    const data = window.btoa(`${auth.value.email}:${auth.value.password}`);

    userStore.postLogin(
      data,
      (res) => {
        if (res.status == HttpStatusCode.Ok) {
          userStore.user = res.data.substring(0, 1);
          console.log(res.data.substring(0, 1));
          router.push("/");
        }
      },
      (err) => {
        console.error(err);
        loginErrors.value.push("아이디 또는 비밀번호가 일치하지 않습니다.");
      }
    );
  }
};
</script>
<template>
  <v-container class="container">
    <v-col cols="12" align="center" justify="center">
      <v-col>
        <v-img width="20%" src="@/assets/logo-1.svg" class="border-12" />
      </v-col>
      <v-col cols="6" align="center">
        <v-row rows="12">
          <v-col cols="9">
            <v-row align="center"
              ><v-col cols="3"> 이메일 </v-col>
              <v-col cols="9">
                <v-text-field
                  label="이메일을 입력해주세요."
                  variant="solo"
                  v-model="auth.email"
                  :error-messages="emailErrors"
                  @input="clearEmailError"
                  @keyup.enter="handleSubmit"
                ></v-text-field> </v-col
            ></v-row>
            <v-row align="center"
              ><v-col cols="3"> 비밀번호 </v-col>
              <v-col cols="9">
                <v-text-field
                  label="비밀번호를 입력해주세요."
                  variant="solo"
                  v-model="auth.password"
                  :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showPassword ? 'text' : 'password'"
                  @click:append="showPassword = !showPassword"
                  :error-messages="passwordErrors"
                  @input="clearPasswordError"
                  @keyup.enter="handleSubmit"
                ></v-text-field> </v-col
            ></v-row>
          </v-col>
          <v-col cols="2">
            <v-btn width="100%" height="88%" @click="handleSubmit"
              >로그인</v-btn
            >
          </v-col>
        </v-row>
        <v-col cols="12" align="center">
          <v-col v-if="loginErrors">
            <div class="error-message">{{ loginErrors[0] }}</div>
          </v-col>
        </v-col>
        <v-row
          rows="12"
          justify="center"
          style="padding-left: 12px; padding-right: 12px"
        >
          <v-col cols="4"><v-btn width="100%">아이디 찾기</v-btn></v-col>
          <v-col cols="4"><v-btn width="100%">비밀번호 찾기</v-btn></v-col>
          <v-col cols="4"
            ><RouterLink to="/signup"
              ><v-btn width="100%">회원가입</v-btn></RouterLink
            ></v-col
          >
        </v-row>
        <v-col> </v-col>
        <!-- <v-col>
          <v-btn width="100%">카카오 로그인</v-btn>
        </v-col>
        <v-col>
          <v-btn width="100%">구글 로그인</v-btn>
        </v-col>
        <v-col>
          <v-btn width="100%">네이버 로그인</v-btn>
        </v-col> -->
      </v-col>
    </v-col>
  </v-container>
</template>

<style scoped>
.border-12 {
  border-radius: 12px;
}
.error-message {
  font-size: 18px;
  color: red;
}
</style>
