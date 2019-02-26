import { Moment } from 'moment';
import { IEskalasyonTuru } from 'app/shared/model/eskalasyon-turu.model';

export interface IEskalasyon {
    id?: number;
    deger?: number;
    tarih?: Moment;
    turu?: IEskalasyonTuru;
}

export class Eskalasyon implements IEskalasyon {
    constructor(public id?: number, public deger?: number, public tarih?: Moment, public turu?: IEskalasyonTuru) {}
}
