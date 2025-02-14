const getMyInfo = () => {
    fetch('/member/myinfo.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {console.log(d); jobFind(d.mname);})
    .catch(e => console.log(e))
}

getMyInfo();

const jobFind = (mname) => {

    const jono = new URL(location.href).searchParams.get('jono');

    fetch(`/joboffer/find.do?jono=${jono}`,{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        document.querySelector('.mnamebox').innerHTML = d.memberDto.mname;

        document.querySelector('.jotitle').innerHTML = d.jotitle;
        
        if(d.jostate == true){
            document.querySelector('.jostate').innerHTML = '마감';
            } else {document.querySelector('.jostate').innerHTML = '모집 중';};
        
        document.querySelector('.jocontent').innerHTML = d.jocontent;

        document.querySelector('.maddrbox').innerHTML = d.memberDto.maddr;
        document.querySelector('.cdatebox').innerHTML = d.cdate;
        
        if(mname == d.memberDto.mname){
        document.querySelector('.bottomMenu').innerHTML = 
        `<button type="button" onclick="stateUpdate()">마감상태 변경</button>
         <a href="/job/update"><button type="button">수정</button></a>
         <button type="button" onclick="offerDelete()">삭제</button></a>`
        }
    })
    .catch(e => console.log(e))
}

const offerDelete = () => {
 
    const jono = new URL(location.href).searchParams.get('jono');
    
    let result = confirm('삭제 하시겠습니까?');
    if( result == false ) { return; }

    fetch(`/joboffer/delete.do?jono=${jono}`,{method : 'DELETE'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if( d == true ){
            alert("삭제 성공")
            location.href='/';
        } else {'삭제 실패'}
    })
    .catch(e => {console.log(e); alert('삭제 실패')})
}

const stateUpdate = () => {
    const jono = new URL(location.href).searchParams.get('jono');
    
    let result = confirm('변경 하시겠습니까?');
    if( result == false ) { return; }
    
    fetch(`/joboffer/stateupdate.do?jono=${jono}`, {method : 'PUT'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if( d == true ){
            alert("변경 성공"); jobFind();
        } else{'변경 실패'};jobFind();
    })
    .catch(e => {console.log(e); alert('변경 실패')})
}