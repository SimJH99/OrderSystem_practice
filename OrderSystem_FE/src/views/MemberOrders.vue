<script setup>
import OrderListComponenet from '@/components/OrderListComponenet.vue';
import { defineProps } from 'vue';

const props = defineProps({
    id: Number
});
</script>
<template>
    <OrderListComponenet :isAdmin="true" :apiUrl="`/member/${props.id}/orders`"/>
</template>