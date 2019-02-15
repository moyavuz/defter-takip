import { Moment } from 'moment';
import { IEkip } from 'app/shared/model/ekip.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';

export interface IMalzemeTakip {
    id?: number;
    miktar?: number;
    aciklama?: any;
    tarih?: Moment;
    ekip?: IEkip;
    malzeme?: IMalzeme;
}

export class MalzemeTakip implements IMalzemeTakip {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public tarih?: Moment,
        public ekip?: IEkip,
        public malzeme?: IMalzeme
    ) {}
}
