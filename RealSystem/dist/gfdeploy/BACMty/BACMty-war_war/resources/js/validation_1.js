var value;
var patron;
function validateNumber(element){
    value=element.value;
    patron=/[0-9\s]/
    if(!(value=="")){
        for(var i=0;i<value.length;i++){
            if(!patron.test(value[i])){
                alert("El Campo solo admite valores numericos");
                //element.focus();
                break;
            }
        }
    }
}

function validateText(element){
    value=element.value;
    patron =/[A-Za-z\s]/;
    if(!(value=="")){
        for(var i=0;i<value.length;i++){
            if(!patron.test(value[i])){
                alert("El Campo solo admite valores alfabeticos");
                //element.focus();
                break;
            }
        }
    }
}

function validateAlfaNum(element){
    value=element.value;
    patron=/[0-9a-zA-Z\s]/
    if(!(value=="")){
        for(var i=0;i<value.length;i++){
            if(!patron.test(value[i])){
                alert("El Campo solo admite valores alfanumericos");
                //element.focus();
                break;
            }
        }
    }
}

PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    monthNames: ['Enero','Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun','Jul','Ago','Sep','Oct','Nov','Dic'],
    dayNames: ['Domingo','Lunes','Martes','Miércoles','Jueves','Viernes','Sábado'],
    dayNamesShort: ['Dom','Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['D','L','M','X','J','V','S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Sólo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Fecha actual',
    ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'Día',
    allDayText : 'Todo el día'
}; 