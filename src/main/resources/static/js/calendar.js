let date = new Date();

const renderCalendar = () => {

	const viewYear = date.getFullYear();
	const viewMonth = date.getMonth();
	
	const viewAllDay = 
	
	// main 중앙 월 표시
	document.querySelector('.year-month').textContent = `${viewMonth + 1}월`;
	
	// 지난 달 마지막 Date, 이번 달 Date
	const prevLast = new Date(viewYear, viewMonth, 0);
	const thisLast = new Date(viewYear, viewMonth + 1, 0);
	
	const PLDate = prevLast.getDate();
	const PLDay = prevLast.getDay();
	
	const TLDate = thisLast.getDate();
	const TLDay = thisLast.getDay();
	
	// 지난 달, 이번 달, 다음 달 Date 넣는 배열
	const prevDates = [];
	const thisDates = [...Array(TLDate + 1).keys()].slice(1);
	const nextDates = [];
	
	// 지난 달 Dates 계산
	if (PLDay !== 6) {
		
		for (let i = 0; i < PLDay + 1; i++) {
		
			prevDates.unshift(PLDate - i);
		}
	}
	
	// 다음 달 Dates 계산
	for (let i = 1; i < 7 - TLDay; i++) {
			
		nextDates.push(i);
	}
	
	// 지난 달, 다음 달 Dates 합치기
	const dates = prevDates.concat(thisDates, nextDates);
	
	
	// Dates 정리(출력할 태그 작성)
	
	const firstDateIndex = dates.indexOf(1);
	const lastDateIndex = dates.lastIndexOf(TLDate);
	
	dates.forEach((date, i) => {
		
		const months = firstDateIndex > i  ? viewMonth : i < lastDateIndex + 1 ? viewMonth + 1 : viewMonth + 2;
		
		const viewAllDay = viewYear
						 + (months < 10 ? "0" + months : months)
						 + (date < 10 ? "0" + date : date);
		
		const condition = i >= firstDateIndex && i < lastDateIndex + 1 ? 'this' : 'other';
		
		if (i == 0 || i % 7 == 0) {
			
			dates[i] = `<div class = "dates-in">
							<div class="date m-1">
								<a class="date-a ${condition}" id="inChk" href="/accountbook/list/${viewAllDay}">${date}</a>
							</div>`;
		} else if ((i+1) % 7 == 0) {
			
			dates[i] = `	<div class="date m-1" id="inChk">
								<a class="date-a ${condition}" href="/accountbook/list/${viewAllDay}">${date}</a>
							</div>
						</div>`;
		} else {
			
			dates[i] = `	<div class="date m-1" id="inChk">
								<a class="date-a ${condition}" href="/accountbook/list/${viewAllDay}">${date}</a>
							</div>`;
		}
	})
	
	// HTML에 태그 출력
	document.querySelector('.dates').innerHTML = dates.join('');
	
	
	// 오늘 날짜 그리기
	const today = new Date();
	
	if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
		
		for (let date of document.querySelectorAll('.this')) {
			
			if (+date.innerText === today.getDate()) {
				
				date.classList.add('today');
	        	break;
			}
		}
	}
}

renderCalendar();





const prevMonth = () => {
	
	date.setDate(1);
	date.setMonth(date.getMonth() - 1);
	renderCalendar();
}

const nextMonth = () => {
	
	date.setDate(1);
	date.setMonth(date.getMonth() + 1);
	renderCalendar();
}

const goToday = () => {
	
	date = new Date();
	renderCalendar();
}

