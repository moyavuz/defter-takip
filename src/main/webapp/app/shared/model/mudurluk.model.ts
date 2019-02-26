import { IPersonel } from 'app/shared/model/personel.model';
import { IIl } from 'app/shared/model/il.model';

export interface IMudurluk {
    id?: number;
    ad?: string;
    adres?: string;
    mudurlukSorumlu?: IPersonel;
    il?: IIl;
}

export class Mudurluk implements IMudurluk {
    constructor(public id?: number, public ad?: string, public adres?: string, public mudurlukSorumlu?: IPersonel, public il?: IIl) {}
}
