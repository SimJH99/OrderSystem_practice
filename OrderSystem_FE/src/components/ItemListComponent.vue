<script setup>
import axios from 'axios';
import ApiService from '@/router/api.js'
import { useCartStore } from '@/store/useCartStore.js';
import { defineProps, ref, onMounted } from 'vue';

const piniaStore = useCartStore();

const props = defineProps({
    isAdmin: Boolean,
    pageTitle: String
})

const itemList = ref([]);
const pageSize = ref(10);
const currentPage = ref(0);
const searchType = ref('optional');
const searchValue = ref('');
const isLastPage = ref(false);
const selectedItems = ref({});

onMounted(() => {
    window.addEventListener('scroll', scrollPagination);
    loadItems();
})

const loadItems = (async () => {
    try {
        const params = {
            page: currentPage.value,
            size: pageSize.value,
        }
        if(searchType.value === "name"){
            params.name = searchValue.value;
        } else if(searchType.value === "category"){
            params.catogory = searchValue.value;
        }
        console.log("data 호출")
        // const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/items`, {params});
        const response = await ApiService(params);
        const addItemList = response.data.map(item=>({...item, quantity:1}))
        if(addItemList.length < pageSize.value){
            isLastPage.value = true;
        }
    itemList.value = itemList.value.concat(addItemList);
    } catch(error){
        console.log(error)
    }
})

const placeOrder = (async () => {
    const orderItems = Object.keys(selectedItems.value)
                            .filter(key => selectedItems.value[key] === true)
                            .map(key => {
                                const item = itemList.value.find(item => item.id == key);
                                return {itemId:item.id, count:item.quantity};
                            });
    const token = localStorage.getItem('token');
    const headers = token ? {Authorization : `Bearer ${token}`} : {};
    try{
        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/order/create`, orderItems ,{headers});
        alert("주문완료.");
        window.location.reload();
    } catch(error){
        console.log(error);
        alert("주문이 실패되었습니다.");
    }
})

const scrollPagination = (() => {
    const nearBotton = window.innerHeight + window.scrollY >= document.body.offsetHeight - 50;
    if(nearBotton && !isLastPage.value){
        currentPage.value++;
        loadItems();
    }
})

const getImage = ((id) => {
    return`${process.env.VUE_APP_API_BASE_URL}/item/${id}/image`
})

const resetPage = (() => {
    currentPage.value = 0;
    selectedItems.value = [];
    itemList.value = [];
    isLastPage.value = false;
})

const delteItem = (async (itemId) => {
    if(confirm("정말 삭제 하시겠습니까")){
        try{
            const token = localStorage.getItem('token');
            const headers =  {Authorization : `Bearer ${token}`};
            await axios.delete(`${process.env.VUE_APP_API_BASE_URL}/item/${itemId}/delete`, {headers});
            alert("삭제완료");
            window.location.reload();
        } catch(error){
            console.log(error);
            alert("삭제실패");
        }
    }
})

const addCart = (()=> {
    const addCartStore = (item) => piniaStore.addToCart(item);
    const orderItems = Object.keys(selectedItems.value)
                            .filter(key => selectedItems.value[key] === true)
                            .map(key => {
                                const item = itemList.value.find(item => item.id == key);
                                return {itemId : item.id, name : item.name, count : item.quantity};
                            });
    orderItems.forEach(item => addCartStore(item));

    if(orderItems.length < 1){
        alert("주문대상 물건이 없습니다.")
        return;
    }
    if (!confirm(`${orderItems.length}개를 주문하시겠습니까?`)){
        console.log("주문이 취소 되었습니다.");
        return;
    }
})
</script>
<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>{{ props.pageTitle }}</h1></div>
        <div class="d-flex justify-content-between" style="margin-top:20px">
            <form @submit.prevent="loadItems">
                <select v-model="searchType" style="display: inline-block; width:auto; margin-right:5px">
                    <option value="optional">선택</option>
                    <option value="name">상품명</option>
                    <option value="category">카테고리</option>
                </select>
                <input v-model="searchValue" type="text" >
                <button type="submit" @click="resetPage()">검색</button>
            </form>
            <div v-if="!isAdmin">
                <button @click="addCart" class="btn btn-secondary" style="margin: 10px;">장바구니</button>
                <button @click="placeOrder" class="btn btn-success" style="margin: 10px;">주문하기</button>
            </div>
            <div v-if="isAdmin">
                <a href="/item/create"><button class="btn btn-success" style="margin: 10px;">상품등록</button>
                </a>
            </div>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th v-if="!props.isAdmin"></th>
                    <th v-if="props.isAdmin">ID</th>
                    <th>제품사진</th>
                    <th>제품명</th>
                    <th>가격</th>
                    <th>재고수량</th>
                    <th v-if="!props.isAdmin">주문수량</th>
                    <th v-if="props.isAdmin">Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in itemList" :key="item.id">
                    <!-- 체크박스를 선택하면 vaule가 true가 담기게 됨 -->
                    <td v-if="!props.isAdmin"><input type="checkbox" v-model="selectedItems[item.id]"></td>
                    <td v-if="props.isAdmin">{{item.id}}</td>
                    <td><img :src="getImage(item.id)" style="height: 100px; width: auto" alt=""></td>
                    <td>{{item.name}}</td>
                    <td>{{item.price}}</td>
                    <td>{{item.stockQuantity}}</td>
                    <td v-if="!props.isAdmin"><input type="number" v-model="item.quantity" min="1" style="width: 60px;"></td>
                    <td v-if="props.isAdmin"><button @click="delteItem(item.id)" class="btn btn-secondary">삭제</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>