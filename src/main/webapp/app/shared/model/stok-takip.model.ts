import { Moment } from 'moment';
import { IEkip } from 'app/shared/model/ekip.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IDepo } from 'app/shared/model/depo.model';

export const enum StokHareketTuru {
    GIRIS = 'GIRIS',
    CIKIS = 'CIKIS'
}

export interface IStokTakip {
    id?: number;
    miktar?: number;
    aciklama?: any;
    tarih?: Moment;
    hareketTuru?: StokHareketTuru;
    ekip?: IEkip;
    malzeme?: IMalzeme;
    depo?: IDepo;
}

export class StokTakip implements IStokTakip {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public tarih?: Moment,
        public hareketTuru?: StokHareketTuru,
        public ekip?: IEkip,
        public malzeme?: IMalzeme,
        public depo?: IDepo
    ) {}
}
