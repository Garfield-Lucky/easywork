package com.easy.work.demo;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class StreamDemo {

   private Random random;
   private List<Student> stuList;
    private Student[] students;
    @Before
    public void init() {
        random = new Random();
        stuList = new ArrayList<Student>() {
            {
                for (int i = 0; i < 100; i++) {
                    add(new Student("student" + i, random.nextInt(50) + 50));
                }
            }
        };

        students = new Student[100];
        for (int i=0;i<30;i++){
            Student student = new Student("user1",i);
            students[i] = student;
        }
        for (int i=30;i<60;i++){
            Student student = new Student("user2",i);
            students[i] = student;
        }
        for (int i=60;i<100;i++){
            Student student = new Student("user3",i);
            students[i] = student;
        }
    }

    @Test
    public void test1() {
        List<String> studentList = stuList.stream()
                .filter(x -> x.getScore() > 85)
                .sorted(Comparator.comparing(Student::getScore).reversed())
                .map(Student::getName)
                .collect(toList());
        System.out.println(studentList);
    }

    @Test
    public void testOptional() {
        List<String> list = new ArrayList<String>() {
            {
                add("user1");
                add("user2");
            }
        };
        Optional<String> opt = Optional.of("andy with u");
        opt.ifPresent(list::add);
        list.forEach(System.out::println);
    }

    @Test
    public void testOptional2() {
        Integer[] arr = new Integer[]{4,5,6,7,8,9};
        Integer result = Stream.of(arr).filter(x->x>9).max(Comparator.naturalOrder()).orElse(-1);
        System.out.println(result);
        Integer result1 = Stream.of(arr).filter(x->x>9).max(Comparator.naturalOrder()).orElseGet(()->-1);
        System.out.println(result1);
        Integer result2 = Stream.of(arr).filter(x->x>9).max(Comparator.naturalOrder()).orElseThrow(RuntimeException::new);
        System.out.println(result2);
    }

    @Test
    public void testCollect1(){
        /**
         * 生成List
         */
        List<Student> list = Arrays.stream(students).collect(toList());
        list.forEach((x)-> System.out.println(x));
        /**
         * 生成Set
         */
        Set<Student> set = Arrays.stream(students).collect(toSet());
        set.forEach((x)-> System.out.println(x));
        /**
         * 如果包含相同的key，则需要提供第三个参数，否则报错
         */
        Map<String,Integer> map = Arrays.stream(students).collect(toMap(Student::getName,Student::getScore,(s,a)->s+a));
        map.forEach((x,y)-> System.out.println(x+"->"+y));
    }
}

@Data
class Student {
    private String name;
    private Integer score;
    public Student(String name, Integer score) {
        this.name = name;
        this.score = score;
    }
}
