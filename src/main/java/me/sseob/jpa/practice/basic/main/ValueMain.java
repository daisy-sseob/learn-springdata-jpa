package me.sseob.jpa.practice.basic.main;

import me.sseob.jpa.practice.basic.Address;

public class ValueMain {

	public static void main(String[] args) {

		// 값 타입의 비교를 해보자
		Address address1 = new Address("seoul", "홍대", "123123");
		Address address2 = new Address("seoul", "홍대", "123123");

		System.out.println("==비교 = " + (address2 == address1));
		System.out.println("equals 비교 = " + address1.equals(address2));
	}
}