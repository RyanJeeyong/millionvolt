<template>
    <div id="app">
      <!-- 로고 -->
      <a href="../templates/main.html">
        <img src="images/logo.png" alt="Logo" class="logo" />
      </a>
  
      <div class="login-container">
        <h2>새로운 비밀번호 입력</h2>
        <p class="description"># 아이디 : {{ userId }}</p>
  
        <form @submit.prevent="handleSubmit">
          <div class="input-group">
            <input
              type="password"
              v-model="password"
              placeholder="새 비밀번호"
              required
            />
          </div>
  
          <div class="input-group">
            <input
              type="password"
              v-model="passwordConfirm"
              placeholder="새 비밀번호를 재확인"
              required
            />
          </div>
  
          <button type="submit" class="login-button">비밀번호 변경</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import api from '@/axios'
  export default {
    data() {
      return {
        userId: this.$route.query.userId || '',
        password: "",
        passwordConfirm: "",
      };
    },
    created(){
      this.userId = this.$route.query.userId || '';  // 쿼리 파라미터가 존재하면 userId를 할당
    },
    methods: {
      async handleSubmit() {
        // 비밀번호 변경 API 호출
        if (this.password === this.passwordConfirm) {
          try{
            const response = await api.post(`/resetPwd`,{
              password: this.password,
              userId: this.userId,
              username: this.$route.query.username,
              email : this.$route.query.email,
            })
            if(response.status === 200) {
              alert("비밀번호가 성공적으로 변경되었습니다.");
              alert("로그인 화면으로 이동합니다.");
              this.$router.replace({path: '/login' })
            }
          }catch(error){
            alert("비밀번호 초기화 중 에러가 발생하였습니다. " + error);
          }
        } else {
          alert("비밀번호가 일치하지 않습니다.");
        }
      },
    },

    beforeRouteUpdate(to, from, next) {
      // 쿼리 파라미터에서 userId, username, email 확인
      if (to.query.userId) {
        this.userId = to.query.userId;
      }
      if (to.query.username) {
        this.username = to.query.username;
      }
      if (to.query.email) {
        this.email = to.query.email;
      }

      // 모든 값이 업데이트된 후 next 호출
      next();
    }
  };
  </script>
  
  <style scoped>
  /* 전체 페이지 스타일 */
  body {
    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
    background-color: white;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }
  
  /* 로고 스타일 */
  .logo {
    position: absolute;
    top: 5px;
    left: 5px;
    width: 150px; /* 로고 크기 조절 */
  }
  
  /* 로그인 컨테이너 스타일 */
  .login-container {
    width: 500px;
    height: 500px;
    background-color: white;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    position: relative;
    border: 1px solid #c4c4c4;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  
  /* 제목 스타일 */
  h2 {
    font-size: 34px;
    margin-bottom: 50px;
  }
  
  /* 입력 그룹 스타일 */
  .input-group {
    margin-bottom: 10px;
    width: 100%;
    display: flex;
    justify-content: center;
  }
  
  .input-group input {
    width: 400px;
    height: 40px; /* 높이 설정 */
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
  }
  
  .input-group input:focus {
    outline: none; /* 기본 포커스 테두리 제거 */
  }
  
  /* 로그인 버튼 스타일 */
  .login-button {
    width: 430px; /* 버튼 너비 설정 */
    padding: 10px;
    height: 65px; /* 버튼 높이 설정 */
    background-color: #0b0b0b;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 26px;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-bottom: 20px;
    margin-top: 10px;
  }
  
  .login-button:hover {
    background-color: #363636;
  }
  
  .description {
    font-size: 16px;
    color: #666;
    line-height: 1.5;
    margin-bottom: 10px;
    text-align: left;
    padding-left: 30px;
    width: 90%;
  }
  </style>
  