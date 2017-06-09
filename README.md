# 자바 웹 프로그래밍 최종 점검
## 최종 점검 문서 
* JWP Basic 최종 점검.pdf 문서를 참고해 최종 점검 진행

## 질문에 대한 답변
#### 2. Tomcat 서버를 시작 과정을 설명하시오.


#### 3. http://localhost:8080 으로 요청했을 때의 과정을 설명하시오.


#### 6. QuestionController가 multi thread에서 문제가 되는 이유를 설명하시오.
QuestionController 인스턴스가 생기는 heap 영역은 thread 들이 공유하는 메모리 영역이다. 따라서 하나의 thread에서 
초기화된 멤버변수가 다른 thread가 초기화될 때 덮어 써질 수 있다.
