const jobFind = () => {

    const jono = new URL(location.href).searchParams.get('jono');

    fetch(`/joboffer/find.do?jono=${jono}`,{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        document.querySelector('.mnamebox').innerHTML = d.memberDto.mname;
        document.querySelector('.cdatebox').innerHTML = d.cdate;

        document.querySelector('.jotitle').innerHTML = d.jotitle;
        document.querySelector('.jocontent').innerHTML = d.jocontent;
    })
    .catch(e => console.log(e))

}

jobFind();
