package com.example.evently.models;

public enum Type {
    OFFLINE{
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }, ONLINE{
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
