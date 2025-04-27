<template>
  <div class="auth-container">
    <div class="auth-card">
      <!-- Tab navigation -->
      <div class="tabs">
        <div 
          class="tab" 
          :class="{ 'active': activeTab === 'login' }"
          @click="activeTab = 'login'"
        >
          Log in
        </div>
        <div class="divider"></div>
        <div 
          class="tab" 
          :class="{ 'active': activeTab === 'signup' }"
          @click="activeTab = 'signup'"
        >
          Sign up
        </div>
      </div>
      
      <div class="divider-horizontal"></div>
      
      <!-- Login Form -->
      <div v-if="activeTab === 'login'" class="form-container">
        <div class="form">
          <div class="input-field">
            <el-input 
              v-model="loginForm.username" 
              placeholder="Username"
              :class="{ 'is-error': loginErrors.username }"
            />
            <div v-if="loginErrors.username" class="error-message">{{ loginErrors.username }}</div>
          </div>
          
          <div class="input-field">
            <el-input 
              v-model="loginForm.password" 
              placeholder="Password" 
              type="password"
              :class="{ 'is-error': loginErrors.password }"
              show-password
            />
            <div v-if="loginErrors.password" class="error-message">{{ loginErrors.password }}</div>
          </div>
          
          <el-button 
            class="auth-button" 
            @click="handleLogin"
            :loading="loading"
            type="primary"
          >
            Log in
          </el-button>
        </div>
      </div>
      
      <!-- Signup Form -->
      <div v-if="activeTab === 'signup'" class="form-container">
        <div class="form">
          <div class="input-field">
            <el-input 
              v-model="signupForm.username" 
              placeholder="Username"
              :class="{ 'is-error': signupErrors.username }"
            />
            <div v-if="signupErrors.username" class="error-message">{{ signupErrors.username }}</div>
          </div>
          
          <div class="input-field">
            <el-input 
              v-model="signupForm.password" 
              placeholder="Password" 
              type="password"
              :class="{ 'is-error': signupErrors.password }"
              show-password
            />
            <div v-if="signupErrors.password" class="error-message">{{ signupErrors.password }}</div>
          </div>
          
          <div class="input-field">
            <el-input 
              v-model="signupForm.confirmPassword" 
              placeholder="Confirm Password" 
              type="password"
              :class="{ 'is-error': signupErrors.confirmPassword }"
              show-password
            />
            <div v-if="signupErrors.confirmPassword" class="error-message">{{ signupErrors.confirmPassword }}</div>
          </div>
          
          <div class="input-field">
            <el-input 
              v-model="signupForm.email" 
              placeholder="Email"
              :class="{ 'is-error': signupErrors.email }"
            />
            <div v-if="signupErrors.email" class="error-message">{{ signupErrors.email }}</div>
          </div>

          <div class="input-field code-field">
            <el-input 
              v-model="signupForm.verificationCode" 
              placeholder="Verification Code"
              :class="{ 'is-error': signupErrors.verificationCode }"
            />
            <el-button 
              class="send-code-button" 
              @click="sendVerificationCode('signup')"
              :disabled="codeCooldown > 0"
              type="primary"
            >
              {{ codeCooldown > 0 ? `${codeCooldown}s` : 'Get Code' }}
            </el-button>
            <div v-if="signupErrors.verificationCode" class="error-message">{{ signupErrors.verificationCode }}</div>
          </div>
          
          <div class="input-field">
            <el-input 
              v-model="signupForm.major" 
              placeholder="Major/Discipline"
              :class="{ 'is-error': signupErrors.major }"
            />
            <div v-if="signupErrors.major" class="error-message">{{ signupErrors.major }}</div>
          </div>
          
          <el-button 
            class="auth-button" 
            @click="handleSignup"
            :loading="loading"
            type="primary"
          >
            Sign up
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { useUserApi } from '@/api/modules/user';

const router = useRouter();

const { login, register, getUserInfo, getVerification } = useUserApi();

const activeTab = ref('login');
const loading = ref(false);
const codeCooldown = ref(0);

const loginForm = reactive({
  username: '1',
  password: '123456'
});

const signupForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  major: '',
  email: '',
  verificationCode: ''
});

const loginErrors = reactive({
  username: '',
  password: ''
});

const signupErrors = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  major: '',
  email: '',
  verificationCode: ''
});

const validateEmail = (email) => {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regex.test(email);
};

const validateLoginForm = () => {
  let isValid = true;
  
  loginErrors.username = '';
  loginErrors.password = '';
  
  if (!loginForm.username) {
    loginErrors.username = 'Username is required';
    isValid = false;
  }
  
  if (!loginForm.password) {
    loginErrors.password = 'Password is required';
    isValid = false;
  } else if (loginForm.password.length < 6) {
    loginErrors.password = 'Password must be at least 6 characters';
    isValid = false;
  }
  
  return isValid;
};

const validateSignupForm = () => {
  let isValid = true;
  
  signupErrors.username = '';
  signupErrors.password = '';
  signupErrors.confirmPassword = '';
  signupErrors.major = '';
  signupErrors.email = '';
  signupErrors.verificationCode = '';
  
  if (!signupForm.username) {
    signupErrors.username = 'Username is required';
    isValid = false;
  }
  
  if (!signupForm.password) {
    signupErrors.password = 'Password is required';
    isValid = false;
  } else if (signupForm.password.length < 6) {
    signupErrors.password = 'Password must be at least 6 characters';
    isValid = false;
  }
  
  if (signupForm.password !== signupForm.confirmPassword) {
    signupErrors.confirmPassword = 'Passwords do not match';
    isValid = false;
  }
  
  if (!signupForm.major) {
    signupErrors.major = 'Major/Discipline is required';
    isValid = false;
  }
  
  if (!signupForm.email) {
    signupErrors.email = 'Email is required';
    isValid = false;
  } else if (!validateEmail(signupForm.email)) {
    signupErrors.email = 'Please enter a valid email address';
    isValid = false;
  }
  
  if (!signupForm.verificationCode) {
    signupErrors.verificationCode = 'Verification code is required';
    isValid = false;
  } else if (signupForm.verificationCode.length !== 6) {
    signupErrors.verificationCode = 'Verification code must be 6 digits';
    isValid = false;
  }
  
  return isValid;
};

const startCooldown = () => {
  codeCooldown.value = 60;
  const timer = setInterval(() => {
    codeCooldown.value--;
    if (codeCooldown.value <= 0) {
      clearInterval(timer);
    }
  }, 1000);
};

const sendVerificationCode = async (type) => {
  if (type !== 'signup') return;
  
  const email = signupForm.email;
  
  // Validate email
  if (!email) {
    signupErrors.email = 'Email is required to send verification code';
    return;
  }
  
  if (!validateEmail(email)) {
    signupErrors.email = 'Please enter a valid email address';
    return;
  }
  
  try {
    await getVerification({ email });
    ElMessage.success('Verification code sent to your email');
    startCooldown();
  } catch (error) {
    console.error('Failed to send verification code:', error);
    ElMessage.error(error.message || 'Failed to send verification code');
  }
};

const handleLogin = async () => {
  if (!validateLoginForm()) {
    return;
  }
  
  loading.value = true;
  
  try {
    const response = await login({
      username: loginForm.username,
      password: loginForm.password
    });
    
    localStorage.setItem('token', response.data.token);
    const userInfo = await getUserInfo(response.data.id);
    localStorage.setItem('userInfo', JSON.stringify(userInfo.data));
    ElMessage.success('Login successful');
    router.push('/home');
  } catch (error) {
    console.error('Login failed:', error);
    ElMessage.error(error.msg || 'Login failed, please try again');
  } finally {
    loading.value = false;
  }
};

const handleSignup = async () => {
  if (!validateSignupForm()) {
    return;
  }
  
  loading.value = true;
  
  try {
    const registerData = {
      username: signupForm.username,
      password: signupForm.password,
      major: signupForm.major,
      email: signupForm.email,
      confirmPassword:signupForm.password,
      verificationCode: signupForm.verificationCode
    };
    
    await register(registerData);
    ElMessage.success('Registration successful, please login');
    activeTab.value = 'login';
    loginForm.username = signupForm.username;
    
    // Clear signup form
    signupForm.username = '';
    signupForm.password = '';
    signupForm.confirmPassword = '';
    signupForm.major = '';
    signupForm.email = '';
    signupForm.verificationCode = '';
  } catch (error) {
    console.error('Registration failed:', error);
    ElMessage.error(error.message || 'Registration failed, please try again');
  } finally {
    loading.value = false;
  }
};
</script>

<style>
/* Override Element Plus styles for inputs to center the placeholder text */
.auth-container .el-input__inner {
  text-align: center;
}

.auth-container .el-button {
  width: 100%;
  border-radius: 50px;
}
</style>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradientAnimation 15s ease infinite;
  padding: 20px;
}

@keyframes gradientAnimation {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

.auth-card {
  width: 100%;
  max-width: 450px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.auth-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.25);
}

.tabs {
  display: flex;
  position: relative;
}

.tab {
  flex: 1;
  text-align: center;
  padding: 15px 0;
  cursor: pointer;
  font-size: 18px;
  color: #333;
  transition: all 0.3s ease;
  position: relative;
  z-index: 1;
}

.tab.active {
  font-weight: bold;
  color: #fff;
}

.tab.active::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, #ee7752, #e73c7e);
  z-index: -1;
}

.divider {
  width: 1px;
  background-color: #ddd;
}

.divider-horizontal {
  height: 1px;
  background-color: #ddd;
  margin-bottom: 20px;
}

.form-container {
  padding: 20px;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.input-field {
  width: 100%;
  position: relative;
}

.code-field {
  display: flex;
  gap: 10px;
}

.code-field .el-input {
  flex: 1;
}

.send-code-button {
  width: auto !important;
  white-space: nowrap;
  padding: 0 15px;
}

.error-message {
  color: #f56c6c;
  font-size: 12px;
  margin-top: 5px;
  text-align: center;
}

.is-error input {
  border-color: #f56c6c;
}

.auth-button {
  margin-top: 10px;
  background: linear-gradient(90deg, #ee7752, #e73c7e) !important;
  border: none !important;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.auth-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(231, 60, 126, 0.4);
}
</style>