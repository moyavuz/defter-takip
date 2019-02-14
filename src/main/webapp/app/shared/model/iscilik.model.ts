import { IEkip } from 'app/shared/model/ekip.model';
import { IBirim } from 'app/shared/model/birim.model';
import { IPoz } from 'app/shared/model/poz.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IProje } from 'app/shared/model/proje.model';

export interface IIscilik {
    id?: number;
    miktar?: number;
    aciklama?: any;
    resimContentType?: string;
    resim?: any;
    calisanEkips?: IEkip[];
    birims?: IBirim[];
    pozs?: IPoz[];
    malzeme?: IMalzeme;
    projes?: IProje[];
}

export class Iscilik implements IIscilik {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public resimContentType?: string,
        public resim?: any,
        public calisanEkips?: IEkip[],
        public birims?: IBirim[],
        public pozs?: IPoz[],
        public malzeme?: IMalzeme,
        public projes?: IProje[]
    ) {}
}
