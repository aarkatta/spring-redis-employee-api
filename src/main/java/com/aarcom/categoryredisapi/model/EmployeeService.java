package com.aarcom.categoryredisapi.model;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EmployeeService {

    static List<Employee> list = new ArrayList<>();
    private final RedisConnectionFactory redisConnectionFactory;
    private final StringRedisTemplate stringRedisTemplate;

    public EmployeeService(RedisConnectionFactory redisConnectionFactory, StringRedisTemplate stringRedisTemplate) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    static {
        list.add(new Employee(1234, "Bryanna Johnie", "Johnie@aarcomcorp.com"));
        list.add(new Employee(5678, "Dana Jaqueline", "Jaqueline@aarcomcorp.com"));
        list.add(new Employee(2233, "Dune Kerry", "dune@aarcomcorp.com"));
    }

    public List<Employee> getAllEmployees() {
        return list;
    }

    public Employee getEmployeeById(int empId) {
        return list.stream()
                .filter(emp -> emp.getEmployeeId()==empId)
                .findAny().orElse(null);
    }

    public String getEmployeeByName(String name) {
        return stringRedisTemplate.opsForValue().get(name);
    }

    public Employee saveEmployee(Employee emp) {
        emp.setEmployeeId(list.size()+1);
        list.add(emp);
        stringRedisTemplate.opsForValue().set(emp.getName(), emp.toString());
        return emp;
    }

    public Employee deleteEmployee(int empId) {
        Iterator<Employee> iterator = list.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if(empId == emp.getEmployeeId()) {
                iterator.remove();
                return emp;
            }
        }

        return null;

    }

    public void putValue(String key, String value) { stringRedisTemplate.opsForValue().set(key, value);}
    public String getValue(String key) { return stringRedisTemplate.opsForValue().get(key); }


}
