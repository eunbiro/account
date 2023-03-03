/*<![CDATA[*/
var searchDtoList = /*[[$searchDtoList]]*/
/*]]>*/


for (searchDto of searchDtoList) {
	
}
/*
	switch(searchDto.mainCtgId) {
		
		case '1': var income = [searchDto.money, searchDto.mainCtgName];
		break;
		case '2': var saving = [searchDto.money, searchDto.mainCtgName];
		break;
		case '3': var living = [searchDto.money, searchDto.mainCtgName];
		break;
		case '4': var food = [searchDto.money, searchDto.mainCtgName];
		break;
		case '5': var cloth = [searchDto.money, searchDto.mainCtgName];
		break;
		case '6': var hobby = [searchDto.money, searchDto.mainCtgName];
		break;
		case '7': var comm = [searchDto.money, searchDto.mainCtgName];
		break;
		case '8': var car = [searchDto.money, searchDto.mainCtgName];
		break;
		case '9': var insurance = [searchDto.money, searchDto.mainCtgName];
		break;
		case '10': var other = [searchDto.money, searchDto.mainCtgName];
		break;
	}
*/

var context = document.getElementById('myChart').getContext('2d');
var myChart = new Chart(context, {
						type: 'pie', // 차트의 형태
						data: { // 차트에 들어갈 데이터
						
						labels: [
                        //x 축
                        income[1], saving[1], living[1], food[1], cloth[1], hobby[1], comm[1], car[1], insurance[1], other[1]
                    	],
                    	datasets: [
                        { //데이터
                            label: 'test1', //차트 제목
                            fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
                            data: [
                                income[0], saving[0], living[0], food[0], cloth[0], hobby[0], comm[0], car[0], insurance[0], other[0] //x축 label에 대응되는 데이터 값
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
                        }
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