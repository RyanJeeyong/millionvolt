<template>
  <div id="wrap">
    <div class="logo">
      <a href="../templates/main.html">
        <img src="../../../assets/images/logo.png" alt="백만불트 로고" />
      </a>
    </div>

    <div class="container">
      <div class="title">비밀번호 찾기 - 이메일 인증</div>
      <p class="description"># 아이디 : {{ userId }}</p>

      <form class="form" @submit.prevent="handleSubmit">
        <input
          type="text"
          class="input-field"
          placeholder="이름을 입력해주세요."
          v-model="username"
          required
        />

        <div class="email-row">
          <input
            type="email"
            class="input-field email-input"
            placeholder="이메일을 입력해주세요."
            v-model="email"
            required
          />
          <button
            type="button"
            class="verify-button"
            @click="sendVerificationCode"
          >
            인증번호 받기
          </button>
        </div>

        <input
          type="text"
          class="input-field"
          placeholder="인증 번호 6자리 입력"
          v-model="verificationCode"
          required
        />

        <button
          type="submit"
          class="submit-button"
          :disabled="!canSubmit"
        >
          비밀번호 찾기
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import api from '@/axios'

export default {
  data() {
    return {
      userId: this.$route.query.userId || "",
      username: "",
      email: "",
      verificationCode: "",
      sentCode: '',  // 서버에서 받은 인증번호 저장
      isVerified: false,
    };
  },
  computed: {
    canSubmit() {
      return this.username && this.email && this.verificationCode.length === 6;
    },
  },
  methods: {
    async sendVerificationCode() {
      if (!this.email) {
        alert("이메일을 입력해주세요.");
        return;
      }
      // 여기에 API 호출 로직 추가
      try {
          const response = await api.get(`/email/${this.email}`);
          if (response.status === 200) {
            this.sentCode = response.data;  // 서버에서 전송된 인증번호 저장
            console.log("Sent Code:", this.sentCode);
            alert(`인증번호가 ${this.email}로 전송되었습니다.`);
          }
        } catch (error) {
          console.error("인증번호 전송 오류:", error);
          alert("오류가 발생했습니다. 다시 시도해주세요.");
        }
    },
    async handleSubmit() {
      if (this.verificationCode.toString() !== this.sentCode.toString()) {
          console.log("verificationCode : " + this.verificationCode);
          alert("잘못된 인증번호 입니다.");
          this.verificationCode = '';  // 입력 필드 초기화
          return;
        } else if (!this.canSubmit) {
          alert("모든 필드를 입력해주세요.");
          return;
      }
      // 비밀번호 찾기 API 호출 로직 추가
      try{
        const response = await api.get(`/findPwd/${this.username}/${this.email}`);
        if(response.status == 200){
          alert("비밀번호 찾기 요청이 제출되었습니다.");
          this.$router.replace({
            path: '/new-password',
            query: {
              userId: this.userId,
            },
          });
        }
      }catch(error){
        console.error("비밀번호 찾기 오류:", error);
        alert("오류가 발생했습니다. 다시 시도해주세요.");
      }
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.wrap {
  font-family: "Arial", sans-serif;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.container {
  width: 600px;
  height: 600px;
  background-color: #fff;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.logo img {
  position: absolute;
  top: 5px;
  left: 5px;
  width: 150px;
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin-top: 10px;
  margin-bottom: 30px;
}

.description {
  font-size: 16px;
  color: #666;
  line-height: 1.5;
  margin-top: 100px;
  margin-bottom: 10px;
  text-align: left;
  padding-left: 10px;
  width: 90%;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.input-field {
  width: 100%;
  height: 60px;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  margin-bottom: 10px;
}

.email-row {
  display: flex;
  gap: 10px;
}

.email-input {
  flex: 1;
}

.verify-button {
  padding: 12px;
  height: 46px;
  border: 1px solid #595959;
  border-radius: 4px;
  font-size: 12px;
  background-color: #ededed;
  cursor: pointer;
  margin-top: 10px;
}

.verify-button:hover {
  background-color: #e0e0e0;
}

.submit-button {
  padding: 12px;
  background-color: #0b0b0b;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  color: #fff;
  cursor: pointer;
  height: 65px;
}

.submit-button:hover {
  background-color: #464646;
}

.submit-button:disabled {
  cursor: not-allowed;
  background-color: #b1b1b1;
}

.links {
  margin-top: 30px;
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #666;
}

.links a {
  text-decoration: none;
  color: #666;
}

.links a:hover {
  text-decoration: underline;
}
</style>
