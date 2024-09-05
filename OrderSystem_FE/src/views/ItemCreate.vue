<script setup>
import { ref } from 'vue';
import router from '@/router';
import axios from 'axios';

const name = ref('');
const category = ref('');
const price = ref('');
const stockQuantity = ref('');
const imagePath = ref('');

const fileUpload = (event) => {
    imagePath.value = event.target.files[0];
}

const itemCreate = async () => {
    const registerData = new FormData();
    registerData.append("name", name.value.value);
    registerData.append("category", category.value);
    registerData.append("price", price.value);
    registerData.append("stockQuantity", stockQuantity.value);
    registerData.append("imagePath", imagePath.value);
    const token = localStorage.getItem('token');
    const headers = token ? {Authorization : `Bearer ${token}`} : {};
    await axios.post(`${process.env.VUE_APP_API_BASE_URL}/item/create`, registerData ,{headers});
    router.push("/items/manage");
}
</script>
<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>상품등록</h1>
        </div>
        <form @submit.prevent="itemCreate">
            <div class="form-group">
                <label>상품명</label>
                <input type="text" class="form-control" v-model="name">
            </div>
            <div class="form-group">
                <label>카테고리</label>
                <input type="text" class="form-control" v-model="category">
            </div>
            <div class="form-group">
                <label>가격</label>
                <input type="number" min="0" class="form-control" v-model="price">
            </div>
            <div class="form-group">
                <label>재고수량</label>
                <input type="number" min="0" class="form-control" v-model="stockQuantity">
            </div>
            <div class="form-group">
                <label>상품이미지</label>
                <!-- change와 @click비교 : 
                    @click은 요소가 클릭될때마다 함수 실행
                    @change는 해당 태그의 값이 변할때 함수실행
                -->
                <input type="file" class="form-control" accept="image/*" @change="fileUpload">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">등록</button>
            </div>
            <div th:if="${errorMessage != null}">
                <input type="hidden" id="error" th:value="${errorMessage}">
            </div>
        </form>
    </div>
</template>