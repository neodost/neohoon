Dockerfile
```dockerfile
# 젠킨스 공식 이미지를 기반으로 시작
FROM jenkins/jenkins:lts

# kubectl 설치
USER root
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" \
    && chmod +x kubectl \
    && mv kubectl /usr/local/bin/kubectl

# 다시 젠킨스 사용자로 전환
USER jenkins

# sudo docker build . -t kkkqwerasdf123/jenkins-kube --no-cache --pull
# sudo docker push kkkqwerasdf123/jenkins-kube
```