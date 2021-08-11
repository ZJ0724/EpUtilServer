<template>
    <div>
        <div>
            {{name}}
        </div>

        <div style="width: 100%;margin-top: 10px;">
            <ep-input v-if="!isSlot" :size="size" v-model="currentValue" style="width: 100%;"></ep-input>
            <slot></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "inputLabel",

        props: {
            name: {
                type: String
            },

            value: {
                type: String
            },

            size: {
                type: String,
                default: ""
            }
        },

        data() {
            return {
                currentValue: this.value,

                isSlot: false
            };
        },

        watch: {
            currentValue(newVal) {
                this.$emit("input", newVal);
            },

            value(val) {
                this.currentValue = val;
            }
        },

        created() {
            this.isSlot = Object.keys(this.$slots).length !== 0;
        }
    }
</script>