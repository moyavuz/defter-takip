import { IPersonel } from 'app/shared/model/personel.model';

export interface IBolge {
    id?: number;
    ad?: string;
    adres?: string;
    bolgeSorumlu?: IPersonel;
}

export class Bolge implements IBolge {
    constructor(public id?: number, public ad?: string, public adres?: string, public bolgeSorumlu?: IPersonel) {}
}
