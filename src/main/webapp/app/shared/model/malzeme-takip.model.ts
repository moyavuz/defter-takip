import { Moment } from 'moment';
import { IEkip } from 'app/shared/model/ekip.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';

export interface IMalzemeTakip {
    id?: number;
    miktar?: number;
    aciklama?: any;
    tarih?: Moment;
    malzemeAlanEkip?: IEkip;
    malzemeTakipProje?: IMalzeme;
}

export class MalzemeTakip implements IMalzemeTakip {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public tarih?: Moment,
        public malzemeAlanEkip?: IEkip,
        public malzemeTakipProje?: IMalzeme
    ) {}
}
