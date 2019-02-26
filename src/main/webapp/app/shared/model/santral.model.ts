import { IMudurluk } from 'app/shared/model/mudurluk.model';
import { IPersonel } from 'app/shared/model/personel.model';

export interface ISantral {
    id?: number;
    ad?: string;
    mudurluk?: IMudurluk;
    santralSorumlu?: IPersonel;
}

export class Santral implements ISantral {
    constructor(public id?: number, public ad?: string, public mudurluk?: IMudurluk, public santralSorumlu?: IPersonel) {}
}
