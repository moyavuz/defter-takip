import { Moment } from 'moment';
import { IEkip } from 'app/shared/model/ekip.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';

export interface IMalzemeTakip {
    id?: number;
    miktar?: number;
    aciklama?: any;
    tarih?: Moment;
    ekipMalzemeTakip?: IEkip;
    malzemeMalzemeTakip?: IMalzeme;
}

export class MalzemeTakip implements IMalzemeTakip {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public tarih?: Moment,
        public ekipMalzemeTakip?: IEkip,
        public malzemeMalzemeTakip?: IMalzeme
    ) {}
}
