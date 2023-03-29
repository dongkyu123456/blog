let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save()
            // console.log("click log");
        });
        // 누군가가 btnsave에서 on.event를 발동할 경우 함수를 실행함
    },

    save: function () {
        // console.log("save log");
        let data ={
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val(),
        }
        // console.log(data);

        $.ajax().done().fail(); // 에이잭스 통신을 이용해서 3개의 데이터를 json 변경후 insert 요청

    }
}


// 읽을 때 아무 일도 일어나지 않기 때문에 이닛 함수를 호출하여 바인드함
index.init();



//왜 Ajax를 사용해?? 
    //비동기 통신을 하기 위해
        //프로그램을 일의 순서에 맞게 일을 처리함, 비동기처리를 할 시 효율이 좋아짐

    //데이터 서버와 html서버를 따로 쓰지 않고 통일하기 위해
        // 응답을 html이 아니라 Data(json)으로 받기 위해
            // 왜 Data로 받고싶어하는지?
                // 만약 안드로이드 어플리케이션으로 회원가입을 요청할 경우
                // 서버는 DB에게 수행을 함
                // DB는 정상이라고 서버에 리턴함
                // 서버는 HTML을 리턴할 수 없어서 작동하지 않음 자바 코드를 리턴해줘야하는데 
                // 그럴 수 없으니 서버는 데이터를 리턴함


//1클라이언트가 요청을 하면 
//2서버는 html으로 반환하고 
//3브라우저가 읽음


// 콜백 = 하던 일 멈추고 원래 하던 일로 돌아가는 것