<script setup>
import OrderListComponenet from '@/components/OrderListComponenet.vue';
</script>
<template>
    <OrderListComponenet :isAdmin="true" apiUrl="/orders"/>
</template>