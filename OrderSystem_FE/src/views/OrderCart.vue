<script setup>
import axios from 'axios'; 
//pinia의 값을 동적으로 변경해주기위해서 사용
import { storeToRefs } from 'pinia';
import { useCartStore } from '@/store/useCartStore.js';

const piniaStore = useCartStore();

const { getCartItems, getTotalQuantity } = storeToRefs(piniaStore);

const placeOrder = async () => {
    const orderItems = getCartItems.value.map(item =>{
        return {itemId:item.itemId, count:item.count};}
    )
    if(getTotalQuantity.value < 1){
        alert("장바구니에 물건이 없습니다.")
        return;
    }
    if (!confirm(`${getTotalQuantity.value}개를 주문하시겠습니까?`)){
        console.log("주문이 취소 되었습니다.");
        return;
    }
    
    const token = localStorage.getItem('token');
    const headers = token ? {Authorization : `Bearer ${token}`} : {};
    try{
        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/order/create`, orderItems ,{headers});
        console.log(orderItems);
        alert("주문완료.");
        clearCart();
    } catch(error){
        console.log(error);
        alert("주문이 실패되었습니다.");
    }
}

const clearCart = () => {
    piniaStore.clearCart();
}

</script>

<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>장바구니 목록</h1></div>
        <div class="d-flex justify-content-between">
            <button class="btn btn-secondory" @click="clearCart">장바구니 비우기</button>
            <button class="btn btn-success" @click="placeOrder">주문하기</button>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>제품 id</th>
                    <th>제품명</th>
                    <th>주문수량</th>
                </tr>
            </thead>
            <tbody>
            <tr v-for="item in getCartItems" :key="item.itemId">
                <td>{{item.itemId}}</td>
                <td>{{item.name}}</td>
                <td>{{item.count}}</td>
            </tr>
            </tbody>
        </table>
       
    </div>
</template>
