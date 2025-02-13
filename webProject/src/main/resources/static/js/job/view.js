const jobFind = () => {

    const jono = new URL(location.href).searchParams.get('jono');

    fetch(`/joboffer/find.do?jono=${jono}`,{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        document.querySelector('.mnamebox').innerHTML = d.memberDto.mname;

        document.querySelector('.jotitle').innerHTML = d.jotitle;
        document.querySelector('.jocontent').innerHTML = d.jocontent;

        document.querySelector('.maddrbox').innerHTML = d.memberDto.maddr;
        document.querySelector('.cdatebox').innerHTML = d.cdate;

    })
    .catch(e => console.log(e))

}

jobFind();
