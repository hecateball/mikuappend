# mikuappend
## How to run
```
docker build -t mikuappend .
docker run -d -v /var/log/mikuappend:/var/log/mikuappend -p 8080:8080 --name mikuappend mikuappend
```
