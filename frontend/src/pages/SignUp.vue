<script setup lang="ts">
import { ref } from "vue";
import { SignUp } from "@/types";
import { useUserStore } from "@/store/user";
import { HttpStatusCode } from "axios";
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();

const data = ref<SignUp>({
  username: "",
  email: "",
  password: "",
});
const agreedTerms = ref(false);

const emailError = ref("");
const passwordError = ref("");
const nameError = ref("");
const agreedTermsError = ref("");
const signUpError = ref("");

const validateEmail = () => {
  if (!data.value.email.trim()) {
    emailError.value = "이메일을 입력해주세요.";
    return false;
  }
  return true;
};

const validatePassword = () => {
  if (!data.value.password.trim()) {
    passwordError.value = "비밀번호를 입력해주세요.";
    return false;
  }
  return true;
};

const validateName = () => {
  if (!data.value.username.trim()) {
    nameError.value = "이름을 입력해주세요.";
    return false;
  }
  return true;
};

const validateAgreedTerms = () => {
  if (!agreedTerms.value) {
    agreedTermsError.value = "약관에 동의해주세요.";
    return false;
  }
  return true;
};

const handleSubmit = () => {
  emailError.value = "";
  passwordError.value = "";
  nameError.value = "";
  agreedTermsError.value = "";

  if (
    validateEmail() &&
    validatePassword() &&
    validateName()
    // validateAgreedTerms()
  ) {
    requestSignUp();
  } else {
  }
};

const requestSignUp = () => {
  userStore.postSignUp(
    data.value,
    (res) => {
      if (res.status == HttpStatusCode.Ok) {
        router.push("/login");
        console.log(res.data);
      }
    },
    (err) => {
      console.error(err);
    }
  );
};
</script>
<template>
  <v-container class="container">
    <v-col cols="12" align="center" justify="center">
      <v-col>
        <v-img width="20%" src="@/assets/logo-1.svg" class="border-12" />
      </v-col>
      <v-col cols="5">
        <v-row rows="12">
          <v-col cols="12">
            <v-row
              ><v-col cols="12" align="start" class="label"> 이메일 </v-col>
              <v-col cols="12">
                <v-text-field
                  label="email@dongdongju.co.kr"
                  variant="solo"
                  v-model="data.email"
                  :error-messages="emailError"
                ></v-text-field> </v-col
            ></v-row>
            <v-row
              ><v-col cols="12" align="start" class="label"> 비밀번호 </v-col>
              <v-col cols="12">
                <v-text-field
                  label="비밀번호를 입력해주세요."
                  variant="solo"
                  v-model="data.password"
                  :error-messages="passwordError"
                ></v-text-field> </v-col
            ></v-row>
            <v-row
              ><v-col cols="12" align="start" class="label"> 이름 </v-col>
              <v-col cols="12">
                <v-text-field
                  label="이름입력"
                  variant="solo"
                  v-model="data.username"
                  :error-messages="nameError"
                ></v-text-field></v-col
            ></v-row>

            <v-row
              ><v-col cols="12" align="start" class="label"> 약관 </v-col>
              <v-col cols="12">
                <v-col
                  cols="8"
                  style="border: 1px solid lightgray; border-radius: 10px"
                >
                  <v-checkbox style="margin-top: -15px; margin-bottom: -20px"
                    ><template v-slot:label
                      >(필수) 개인정보 약관 동의</template
                    ></v-checkbox
                  >
                  <v-checkbox style="margin-top: -15px; margin-bottom: -20px"
                    ><template v-slot:label
                      >(필수) 개인정보 약관 동의</template
                    ></v-checkbox
                  >
                  <v-checkbox style="margin-top: -15px; margin-bottom: -20px"
                    ><template v-slot:label
                      >(선택) 개인정보 약관 동의</template
                    ></v-checkbox
                  >
                </v-col>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <v-btn
                  width="100%"
                  size="large"
                  style="margin-bottom: 300px"
                  @click="handleSubmit"
                  >회원 가입하기</v-btn
                >
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
    </v-col>
  </v-container>
</template>

<style scoped>
.border-12 {
  border-radius: 12px;
}
.label {
  padding: 0px;
  padding-left: 12px;
}
</style>
