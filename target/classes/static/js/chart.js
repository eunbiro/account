/*<![CDATA[*/
var ctg = new Array();
ctg = /*[[${AccountBookDto.subCategoryDto.mainCategoryDto.id}]]*/;
var money = new Array();
money = /*[[${AccountBookDto.money}]]*/;

var AccountBookDto = /*[[$AccountBookDto]]*/;
/*]]>*/
for (ctg of AccountBookDto) {
	
	switch(ctg.subCategoryDto.mainCategoryDto.id) {
		
		case '1': var income = ctg.money;
		break;
		case '2': var saving = ctg.money;
		break;
		case '3': var living = ctg.money;
		break;
		case '4': var food = ctg.money;
		break;
		case '5': var cloth = ctg.money;
		break;
		case '6': var hobby = ctg.money;
		break;
		case '7': var comm = ctg.money;
		break;
		case '8': var car = ctg.money;
		break;
		case '9': var insurance = ctg.money;
		break;
		case '10': var other = ctg.money;
		break;
	}
}

var context = document.getElementById('myChart').getContext('2d');
var myChart = new Chart(context, {
						type: 'pie', // 차트의 형태
						data: { // 차트에 들어갈 데이터
						
						labels: [
                        //x 축
                        '소득','저축','생활비','식비','의류잡화비','여가비','통신비', '교통비', '보험비', '기타'
                    	],
                    	datasets: [
                        { //데이터
                            label: 'test1', //차트 제목
                            fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
                            data: [
                                income,saving,living,food,cloth,hobby,comm,car,insurance,other //x축 label에 대응되는 데이터 값
                            ],
                            backgroundColor: [
                                //색상
                                'rgba(255, 051, 153, 0.2)',
                                'rgba(204, 051, 000, 0.2)',
                                'rgba(051, 204, 153, 0.2)',
                                'rgba(204, 051, 255, 0.2)',
                                'rgba(255, 102, 000, 0.2)',
                                'rgba(153, 051, 000, 0.2)',
                                'rgba(000, 102, 204, 0.2)',
                                'rgba(153, 255, 204, 0.2)',
                                'rgba(255, 153, 000, 0.2)',
                                'rgba(102, 000, 204, 0.2)'
                            ],
                            borderColor: [
                                //경계선 색상
                                'rgba(255, 051, 153, 1)',
                                'rgba(204, 051, 000, 1)',
                                'rgba(051, 204, 153, 1)',
                                'rgba(204, 051, 255, 1)',
                                'rgba(255, 102, 000, 1)',
                                'rgba(153, 051, 000, 1)',
                                'rgba(000, 102, 204, 1)',
                                'rgba(153, 255, 204, 1)',
                                'rgba(255, 153, 000, 1)',
                                'rgba(102, 000, 204, 1)'
                            ],
                            borderWidth: 1 //경계선 굵기
                        }/* ,
                        {
                            label: 'test2',
                            fill: false,
                            data: [
                                8, 34, 12, 24
                            ],
                            backgroundColor: 'rgb(157, 109, 12)',
                            borderColor: 'rgb(157, 109, 12)'
                        } */
                    ]
                },
                options: {
                    scales: {
                        yAxes: [
                            {
                                ticks: {
                                    beginAtZero: true
                                }
                            }
                        ]
                    }
                }
            });