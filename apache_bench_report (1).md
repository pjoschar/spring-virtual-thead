
# Apache Bench Benchmark Report

This report compares three different endpoints using ApacheBench (`ab`) to evaluate throughput, latency, and overall performance.

---

## 🔹 `/reports/US` — Traditional (Baseline)

```
Server Software:        
Server Hostname:        localhost
Server Port:            9191

Document Path:          /reports/US
Document Length:        33 bytes

Concurrency Level:      10
Time taken for tests:   0.144 seconds
Complete requests:      50
Failed requests:        0
Total transferred:      8300 bytes
HTML transferred:       1650 bytes
Requests per second:    347.13 [#/sec] (mean)
Time per request:       28.808 [ms] (mean)
Time per request:       2.881 [ms] (mean, across all concurrent requests)
Transfer rate:          56.27 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   1.8      1       7
Processing:    12   24   7.2     23      45
Waiting:       11   24   7.0     22      44
Total:         12   26   6.9     24      45

Percentage of the requests served within a certain time (ms)
  50%     24
  66%     26
  75%     28
  80%     30
  90%     38
  95%     45
  98%     45
  99%     45
 100%     45 (longest request)
```

---

## 🔹 `/reports/platform/US` — Platform Thread

```
Server Software:        
Server Hostname:        localhost
Server Port:            9191

Document Path:          /reports/platform/US
Document Length:        49 bytes

Concurrency Level:      10
Time taken for tests:   0.099 seconds
Complete requests:      50
Failed requests:        0
Total transferred:      9100 bytes
HTML transferred:       2450 bytes
Requests per second:    507.48 [#/sec] (mean)
Time per request:       19.705 [ms] (mean)
Time per request:       1.971 [ms] (mean, across all concurrent requests)
Transfer rate:          90.20 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    2   2.9      0      11
Processing:     3   16   5.5     16      32
Waiting:        2   15   5.0     15      32
Total:          3   18   6.2     18      32

Percentage of the requests served within a certain time (ms)
  50%     18
  66%     21
  75%     21
  80%     23
  90%     28
  95%     30
  98%     32
  99%     32
 100%     32 (longest request)
```

---

## 🔹 `/reports/virtual/US` — Virtual Thread

```
Server Software:        
Server Hostname:        localhost
Server Port:            9191

Document Path:          /reports/virtual/US
Document Length:        48 bytes

Concurrency Level:      10
Time taken for tests:   0.057 seconds
Complete requests:      50
Failed requests:        0
Total transferred:      9050 bytes
HTML transferred:       2400 bytes
Requests per second:    878.56 [#/sec] (mean)
Time per request:       11.382 [ms] (mean)
Time per request:       1.138 [ms] (mean, across all concurrent requests)
Transfer rate:          155.29 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   1.2      0       6
Processing:     2    9   5.0      9      29
Waiting:        2    9   4.9      9      29
Total:          2   10   5.2     11      29

Percentage of the requests served within a certain time (ms)
  50%     11
  66%     12
  75%     13
  80%     13
  90%     18
  95%     19
  98%     29
  99%     29
 100%     29 (longest request)
```

---

## 📈 Chart Summary

![Benchmark Chart](benchmark_comparison_chart.png)


---

## 📊 Benchmark Comparison Table

| Metric                          | `/reports/US` (Baseline) | `/platform/US` (Platform Thread) | `/virtual/US` (Virtual Thread) |
|--------------------------------|---------------------------|----------------------------------|---------------------------------|
| **Total Requests**             | 50                        | 50                               | 50                              |
| **Concurrency Level**          | 10                        | 10                               | 10                              |
| **Test Duration (sec)**        | 0.144                     | 0.099                            | 0.057                           |
| **Requests/sec**               | 347.13                    | 507.48                           | **878.56**                      |
| **Mean Time/request (ms)**     | 28.81                     | 19.70                            | **11.38**                       |
| **Time/request per thread (ms)**| 2.88                      | 1.97                             | **1.13**                        |
| **Transfer Rate (KB/s)**       | 56.27                     | 90.20                            | **155.29**                      |
| **Median Latency (ms)**        | 24                        | 18                               | **11**                          |
| **95th Percentile Latency (ms)**| 45                        | 30                               | **19**                          |
| **Max Latency (ms)**           | 45                        | 32                               | **29**                          |

---

